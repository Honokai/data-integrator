import { Script } from "@/lib/types";
import apiClient from "@/services/api/apiClient";
import { useQuery } from "@tanstack/react-query";

export function useScript(scriptId: string) {
  return useQuery<Script, Error>({
    queryKey: ["script", scriptId],
    queryFn: () =>
      apiClient.get(`/scripts/${scriptId}`).then((res) => res.data),
    staleTime: 1000 * 60 * 5,
  });
}
