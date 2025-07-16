import apiClient from "../apiClient";

export async function listMachines() {
  const { data, status } = await apiClient.get("/machines");

  return { data, status };
}

export async function getMachineData(machineId: string) {
  const { data, status } = await apiClient.get(`/machines/${machineId}`);

  return { data, status };
}

export async function listTasksRelatedToMachine(machineId: string) {
  const { data, status } = await apiClient.get(`/machines/${machineId}/tasks`);

  return { data, status };
}
