"use client";

import { Checkbox } from "@/components/ui/checkbox";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import apiClient from "@/services/api/apiClient";
import { Check, OctagonX } from "lucide-react";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export type Machine = {
  id: string;
  name: string;
  ip: string;
  active: boolean;
};

export default function MachineIndex() {
  const [machines, setMachines] = useState<Machine[]>([] as Machine[]);
  const router = useRouter();
  const [checkedMachines, setCheckedMachines] = useState<{
    [key: string]: boolean;
  }>({});

  const fetchMachines = async () => {
    await apiClient
      .get("/machines")
      .then((res) => setMachines(res.data))
      .catch((err) => {
        console.error("Error fetching machines:", err);
      });
  };

  useEffect(() => {
    fetchMachines();
  }, []);

  return (
    <main className="flex-1 container mx-auto">
      <header className="my-4">
        <h1 className="text-2xl">Machines</h1>
        <h2 className="text-xl text-muted-foreground">
          List of active/inactive machines
        </h2>
      </header>
      <section title="Machines table">
        <Table border={1} className="w-full">
          <TableHeader className="border-y-0">
            <TableRow className="[&>*]:font-bold">
              <TableHead className="flex items-center">
                <Checkbox />
              </TableHead>
              <TableHead>ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>IP</TableHead>
              <TableHead>Active</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {machines.map((machine) => (
              <TableRow
                key={machine.id}
                onDoubleClick={() => router.push(`/machines/${machine.id}`)}
              >
                <TableCell className="flex items-center">
                  <Checkbox
                    value={machine.id}
                    checked={checkedMachines[machine.id]}
                  />
                </TableCell>
                <TableCell>{machine.id}</TableCell>
                <TableCell>{machine.name}</TableCell>
                <TableCell>{machine.ip}</TableCell>
                <TableCell>
                  {machine.active ? (
                    <Check className="text-success" size={20} />
                  ) : (
                    <OctagonX className="text-danger" size={20} />
                  )}
                </TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </section>
    </main>
  );
}
