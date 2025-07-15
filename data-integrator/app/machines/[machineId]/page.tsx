"use client";

import React, { useEffect } from "react";
import { Machine } from "../page";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Checkbox } from "@/components/ui/checkbox";
import { useParams, useRouter } from "next/navigation";
import { BadgeQuestionMarkIcon, Check, OctagonX, PlusIcon } from "lucide-react";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
  TableCaption,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import {
  HoverCard,
  HoverCardContent,
  HoverCardTrigger,
} from "@/components/ui/hover-card";
import apiClient from "@/services/api/apiClient";
import Link from "next/link";

export type Task = {
  id: string;
  machine: string;
  networkPath: string;
  singleFile: boolean;
  scanInterval: number;
  active: boolean;
  fileFilter: {
    pattern: string;
    type: string;
  };
};

export default function MachineEdit() {
  const { machineId } = useParams<{ machineId: string }>();
  const [machine, setMachine] = React.useState<Machine | null>(null);
  const [tasks, setTasks] = React.useState<Task[]>([]);
  const router = useRouter();

  const fetchMachineData = async () => {
    const { data } = await apiClient.get(`/machines/${machineId}`);

    if (data != null) {
      setMachine(data);
    } else {
      console.error("Error fetching machine data");
    }
  };

  const fetchTasksRelatedToMachine = async () => {
    const { data } = await apiClient.get(`/machines/${machineId}/tasks`);

    if (data != null) {
      setTasks(data);
    }
  };

  useEffect(() => {
    fetchMachineData();
    fetchTasksRelatedToMachine();
  }, [machineId]);

  if (!machine) return <p>Loading machine data...</p>;

  return (
    <main className="flex-1 container mx-auto py-4">
      <header>
        <h1 className="text-2xl">Machine</h1>
        <h2 className="text-xl text-muted-foreground">{machineId}</h2>
      </header>
      <div className="p-4">
        <section
          title="Machine details form"
          className="grid grid-cols-3 gap-4 mb-16"
        >
          <div>
            <Label htmlFor="hostname" className="text-md my-2">
              Hostname
            </Label>
            <Input
              id="hostname"
              readOnly={true}
              value={machine.name ?? ""}
              disabled={true}
              className="cursor-not-allowed"
            />
          </div>
          <div>
            <Label htmlFor="ip" className="text-md my-2">
              IP Address
            </Label>
            <Input id="ip" readOnly={true} value={machine.ip ?? ""} />
          </div>
          <div className="flex flex-col justify-center">
            <Label htmlFor="active" className="text-md my-2">
              Active
            </Label>
            <Checkbox
              id="active"
              checked={machine.active ? true : false}
              onCheckedChange={() =>
                setMachine({
                  ...machine,
                  active: !machine.active,
                })
              }
            />
          </div>
        </section>
        <section
          title="Table with tasks associated with this machine"
          className=""
        >
          <div className="flex">
            <div className="flex flex-1 justify-between">
              <p className="text-2xl mb-2">
                Tasks related to current machine
                <HoverCard>
                  <HoverCardTrigger asChild>
                    <Button variant="link">
                      <BadgeQuestionMarkIcon />
                    </Button>
                  </HoverCardTrigger>
                  <HoverCardContent className="w-80">
                    <div className="flex justify-between gap-4 bg-background">
                      <div className="space-y-1">
                        <h4 className="text-sm font-semibold">@nextjs</h4>
                        <p className="text-sm">
                          The React Framework – created and maintained by
                          @vercel.
                        </p>
                        <div className="text-muted-foreground text-xs">
                          Joined December 2021
                        </div>
                      </div>
                    </div>
                  </HoverCardContent>
                </HoverCard>
              </p>
              <Button className="p-0" asChild>
                <Link href={`/tasks/create?machineId=${machineId}`}>
                  <PlusIcon size={20} /> Task
                </Link>
              </Button>
            </div>
          </div>
          <Table border={1} className="w-full">
            <TableCaption>All tasks related to this machine</TableCaption>
            <TableHeader className="border-y-0">
              <TableRow className="[&>*]:font-bold">
                <TableHead className="flex items-center">
                  <Checkbox />
                </TableHead>
                <TableHead>ID</TableHead>
                <TableHead>Network Path</TableHead>
                <TableHead>Scan Interval</TableHead>
                <TableHead>Single File</TableHead>
                <TableHead>Active</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {tasks.length > 0 ? (
                tasks?.map((task) => (
                  <TableRow
                    key={task.id}
                    onDoubleClick={() => router.push(`/tasks/${task.id}`)}
                  >
                    <TableCell className="flex items-center">
                      <Checkbox value={task.id} />
                    </TableCell>
                    <TableCell>{task.id}</TableCell>
                    <TableCell>{task.networkPath}</TableCell>
                    <TableCell>{task.scanInterval}</TableCell>
                    <TableCell>{task.singleFile ? "Yes" : "No"}</TableCell>
                    <TableCell>
                      {task.active ? (
                        <Check className="text-success" size={20} />
                      ) : (
                        <OctagonX className="text-danger" size={20} />
                      )}
                    </TableCell>
                    <TableCell>Actions</TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={6} className="text-center text-primary">
                    No tasks found
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </section>
      </div>
    </main>
  );
}
