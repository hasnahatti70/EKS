import http from 'k6/http';
import { check, sleep } from 'k6';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

export let options = {
    scenarios: {
        load_test: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '30s', target: 20 },
                { duration: '1m', target: 50 },
                { duration: '30s', target: 0 },
            ],
            exec: 'loadTest'
        },
        stress_test: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '10s', target: 200 },
                { duration: '30s', target: 300 },
                { duration: '10s', target: 0 },
            ],
            exec: 'stressTest'
        },
    },
};

const BASE_URL = 'http://af4cee8febfc8452ca27267062eaad5d-1761563196.us-east-1.elb.amazonaws.com';
let resultats = [];

function genererUtilisateurReel() {
    const prenoms = ['hasna', 'najat', 'meryem', 'slimane', 'Ahmed', 'majda', 'mus', 'moh'];
    const noms = ['Hatti', 'ht', 'tamir', 'hht', 'Leamiri', 'Benali', 'chorfi', 'chtioui'];

    const prenom = prenoms[Math.floor(Math.random() * prenoms.length)];
    const nom = noms[Math.floor(Math.random() * noms.length)];
    const email = `${prenom.toLowerCase()}.${nom.toLowerCase()}${Math.floor(Math.random() * 1000)}@exemple.com`;

    return {
        nom: `${prenom} ${nom}`,
        email: email
    };
}

export function loadTest() {
    runScenario('load');
}

export function stressTest() {
    runScenario('stress');
}

function runScenario(type) {
    const resGet = http.get(`${BASE_URL}/formulaire`);

    check(resGet, {
        'GET Status 200': (r) => r.status === 200,
        'Présence du formulaire': (r) => r.body.includes('<form'),
    });

    const utilisateurData = genererUtilisateurReel();

    const payload = Object.entries(utilisateurData)
        .map(([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(v)}`)
        .join('&');

    const resPost = http.post(`${BASE_URL}/soumettre`, payload, {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    });

    console.log(`[${type}] POST ${resPost.status} | nom: ${utilisateurData.nom}, email: ${utilisateurData.email}`);

    check(resPost, {
        'POST Status 2xx ou 3xx': (r) => r.status >= 200 && r.status < 400,
        'Réponse contient "Merci"': (r) => r.body.includes('Merci') || r.body.includes('merci'),
    });

    resultats.push({
        scenario: type,
        utilisateur: utilisateurData.nom,
        email: utilisateurData.email,
        status: resPost.status,
        duree: resPost.timings.duration.toFixed(2),
    });

    sleep(1);
}

export function handleSummary(data) {
    let csv = 'scenario,utilisateur,email,status,duree(ms)\n';
    resultats.forEach(r => {
        csv += `${r.scenario},${r.utilisateur},${r.email},${r.status},${r.duree}\n`;
    });

    return {
        'resultats.csv': csv,
        stdout: textSummary(data, { indent: ' ', enableColors: true }),
    };
}
