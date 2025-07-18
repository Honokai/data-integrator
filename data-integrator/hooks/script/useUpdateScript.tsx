import { Script } from "@/lib/types";
import apiClient from "@/services/api/apiClient";
import { useMutation, useQueryClient } from "@tanstack/react-query";

export default function useUpdateScript(scriptId: string) {
  const queryClient = useQueryClient();

  return useMutation<Script, Error, Script>({
    mutationFn: (updatedScript) =>
      apiClient
        .put(`/scripts/${scriptId}`, updatedScript)
        .then((res) => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["script", scriptId] });
    },
    onError: (err: Error) => {
      console.error("Erro ao salvar:", err);
    },
  });
}
