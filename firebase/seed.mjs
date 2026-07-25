import { readFile } from "node:fs/promises";
import { Firestore } from "@google-cloud/firestore";

const args = process.argv.slice(2);
const useEmulator = args.includes("--emulator");
const projectId = argumentValue("--project") ?? process.env.GOOGLE_CLOUD_PROJECT;

if (!projectId) {
  throw new Error("Informe o projeto com --project ID ou GOOGLE_CLOUD_PROJECT.");
}

if (useEmulator) {
  process.env.FIRESTORE_EMULATOR_HOST ??= "127.0.0.1:8080";
}

const seedUrl = new URL("./seed-data.json", import.meta.url);
const seed = JSON.parse(await readFile(seedUrl, "utf8"));
validateSeed(seed);

const db = new Firestore({ projectId });
const batch = db.batch();
const competitionRef = db.collection("competitions").doc(seed.id);
const { id: competitionId, teams, ...competitionData } = seed;

batch.set(competitionRef, competitionData, { merge: true });

let playerCount = 0;
for (const team of teams) {
  const { id: teamId, players, ...teamData } = team;
  const teamRef = competitionRef.collection("teams").doc(teamId);
  batch.set(teamRef, teamData, { merge: true });

  for (const player of players) {
    const { id: playerId, ...playerData } = player;
    batch.set(teamRef.collection("players").doc(playerId), playerData, { merge: true });
    playerCount += 1;
  }
}

await batch.commit();
console.log(
  `Carga concluida em ${projectId}: 1 competicao, ${teams.length} equipes e ${playerCount} jogadores.`,
);

function argumentValue(name) {
  const index = args.indexOf(name);
  return index >= 0 ? args[index + 1] : undefined;
}

function validateSeed(data) {
  if (!data.id || !data.name || !data.edition || !Array.isArray(data.teams)) {
    throw new Error("seed-data.json possui uma competicao invalida.");
  }

  for (const team of data.teams) {
    if (!team.id || !team.name || !team.coach?.name || !Array.isArray(team.players)) {
      throw new Error(`Equipe invalida na carga: ${team.id ?? "sem id"}.`);
    }
    for (const player of team.players) {
      if (!player.id || !player.name || !player.position || !Number.isInteger(player.number)) {
        throw new Error(`Jogador invalido na equipe ${team.id}.`);
      }
    }
  }
}
