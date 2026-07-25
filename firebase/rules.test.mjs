import { readFile } from "node:fs/promises";
import { after, before, test } from "node:test";
import {
  assertFails,
  assertSucceeds,
  initializeTestEnvironment,
} from "@firebase/rules-unit-testing";
import { doc, getDoc, setDoc } from "firebase/firestore";

const projectId = "demo-album-figurinhas-2026";
const rules = await readFile(new URL("../firestore.rules", import.meta.url), "utf8");
let testEnvironment;

before(async () => {
  testEnvironment = await initializeTestEnvironment({
    projectId,
    firestore: { rules },
  });

  await testEnvironment.withSecurityRulesDisabled(async (context) => {
    await setDoc(doc(context.firestore(), "competitions/copa_mundo_2026"), {
      name: "Copa do Mundo",
      edition: "2026",
    });
    await setDoc(
      doc(context.firestore(), "competitions/copa_mundo_2026/teams/brasil"),
      { name: "Brasil" },
    );
  });
});

after(async () => {
  await testEnvironment?.cleanup();
});

test("permite leitura publica da competicao", async () => {
  const db = testEnvironment.unauthenticatedContext().firestore();
  await assertSucceeds(getDoc(doc(db, "competitions/copa_mundo_2026")));
});

test("permite leitura publica de uma equipe", async () => {
  const db = testEnvironment.unauthenticatedContext().firestore();
  await assertSucceeds(
    getDoc(doc(db, "competitions/copa_mundo_2026/teams/brasil")),
  );
});

test("bloqueia escrita do aplicativo", async () => {
  const db = testEnvironment.unauthenticatedContext().firestore();
  await assertFails(
    setDoc(doc(db, "competitions/copa_mundo_2026"), { name: "Alterado" }),
  );
});

test("bloqueia colecoes fora do album", async () => {
  const db = testEnvironment.unauthenticatedContext().firestore();
  await assertFails(getDoc(doc(db, "private/config")));
});

test("bloqueia subcolecoes desconhecidas dentro da competicao", async () => {
  const db = testEnvironment.unauthenticatedContext().firestore();
  await assertFails(
    getDoc(doc(db, "competitions/copa_mundo_2026/secrets/config")),
  );
});
