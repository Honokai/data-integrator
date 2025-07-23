import apiClient from "@/services/api/apiClient";
import { useQuery } from "@tanstack/react-query";

export default function useScripts() {
  return useQuery({
    queryFn: () => apiClient.get("/scripts").then((res) => res.data),
    queryKey: ["scripts"],
    staleTime: 1000 * 60 * 5,
  });
}
