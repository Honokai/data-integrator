import { Script } from "@/lib/types";
import apiClient from "../apiClient";

export async function createScript(script: Partial<Script>) {
  const { data, status } = await apiClient.post("/scripts", script);

  return { data, status };
}

export async function updateScript(script: Partial<Script>) {
  const { data, status } = await apiClient.put(`/scripts/${script.id}`, script);

  return { data, status };
}
