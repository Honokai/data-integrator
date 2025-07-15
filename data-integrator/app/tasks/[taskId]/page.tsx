"use client";

import { Task } from "@/app/machines/[machineId]/page";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import {
  HoverCard,
  HoverCardContent,
  HoverCardTrigger,
} from "@/components/ui/hover-card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import apiClient from "@/services/api/apiClient";

import { BadgeQuestionMarkIcon, Check, OctagonX, PlusIcon } from "lucide-react";
import Link from "next/link";
import { useParams } from "next/navigation";
import router from "next/router";
import { useCallback, useEffect, useState } from "react";

type Script = {
  id: string;
  version: number;
  instruction: string;
  active: boolean;
};

export default function TaskPage() {
  const { taskId } = useParams<{ taskId: string }>();
  const [task, setTask] = useState<Task | null>(null);
  const [scripts, setScripts] = useState<Script[]>([]);
  const [filterTypes, setFilterTypes] = useState<string[]>([]);

  const fetchTaskData = useCallback(async () => {
    const { data } = await apiClient.get(`/tasks/${taskId}`);

    if (data != null) {
      setTask(data);
    }
  }, [taskId]);

  const fetchScriptsData = useCallback(async () => {
    const { data } = await apiClient.get(`/tasks/${taskId}/scripts`);

    if (data != null) {
      setScripts(data);
    }
  }, [taskId]);

  const fetchFilterTypes = useCallback(async () => {
    const { data } = await apiClient.get("/enums/fileFilterTypes");

    if (data != null) {
      setFilterTypes(data);
    }
  }, []);

  const onFormSubmit = async (ev: React.FormEvent<HTMLFormElement>) => {
    ev.preventDefault();
    if (task) {
      try {
        Object.entries(task).forEach(([key, value]) => {
          console.log(`${key}: ${value}`);
        });
        const { data } = await apiClient.put(
          `/tasks/${taskId}`,
          JSON.stringify(task)
        );
      } catch (error) {
        console.error("Error updating task:", error);
        alert("Failed to update task.");
      }
    }
  };

  useEffect(() => {
    fetchTaskData();
    fetchFilterTypes();
    fetchScriptsData();
  }, [taskId, fetchTaskData]);

  return (
    <main className="flex-1 container mx-auto overflow-hidden w-full p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">Task</CardTitle>
          <CardDescription>{taskId}</CardDescription>
        </CardHeader>
        <CardContent>
          {task ? (
            <section title="Task Details Form Section" className="">
              <form action="#" onSubmit={onFormSubmit}>
                <div className="flex flex-col gap-4">
                  <div className="grid lg:grid-cols-8 gap-4 md:grid-cols-6 sm:grid-cols-2">
                    <div className="col-span-6">
                      <Label className="mb-2" htmlFor="networkPath">
                        Network Path
                      </Label>
                      <Input
                        id="networkPath"
                        name="networkPath"
                        value={task.networkPath}
                        onChange={(e) =>
                          setTask({ ...task, networkPath: e.target.value })
                        }
                      />
                    </div>
                    <div className="col-auto">
                      <Label className="mb-2" htmlFor="filterPattern">
                        Filter
                      </Label>
                      <Input
                        id="filterPattern"
                        name="filterPattern"
                        value={task.fileFilter.pattern}
                        onChange={(e) =>
                          setTask({
                            ...task,
                            fileFilter: {
                              ...task.fileFilter,
                              pattern: e.target.value,
                            },
                          })
                        }
                      />
                    </div>
                    <div className="col-auto">
                      <Label className="mb-2" htmlFor="filterType">
                        Filter
                      </Label>
                      <Select
                        name="filterType"
                        onValueChange={(value) =>
                          setTask({
                            ...task,
                            fileFilter: { ...task.fileFilter, type: value },
                          })
                        }
                        value={task.fileFilter.type}
                      >
                        <SelectTrigger className="w-[180px]">
                          <SelectValue placeholder="Theme" />
                        </SelectTrigger>
                        <SelectContent>
                          {filterTypes.map((type) => (
                            <SelectItem key={type} value={type}>
                              {type}
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    </div>
                  </div>
                  <div className="grid grid-cols-8">
                    <div className="col-span-1">
                      <Label className="mb-2" htmlFor="scanInterval">
                        Scan Interval (seconds)
                      </Label>
                      <Input
                        id="scanInterval"
                        name="scanInterval"
                        type="number"
                        min={5}
                        value={task.scanInterval}
                        onChange={(e) =>
                          setTask({
                            ...task,
                            scanInterval: parseInt(e.target.value, 10),
                          })
                        }
                      />
                    </div>
                    <div className="flex flex-col items-center">
                      <Label htmlFor="active" className="mb-5">
                        Active
                      </Label>
                      <Checkbox
                        id="active"
                        name="active"
                        checked={task.active}
                        onCheckedChange={(checked) =>
                          setTask({
                            ...task,
                            active: checked as boolean,
                          })
                        }
                      />
                    </div>
                    <div className="flex flex-col items-center">
                      <Label htmlFor="singleFile" className="mb-5">
                        Single File
                      </Label>
                      <Checkbox
                        id="singleFile"
                        name="singleFile"
                        checked={task.singleFile}
                        onCheckedChange={(checked) =>
                          setTask({
                            ...task,
                            singleFile: checked as boolean,
                          })
                        }
                      />
                    </div>
                  </div>
                  <div className="flex">
                    <Button type="submit" className="cursor-pointer">
                      Save
                    </Button>
                  </div>
                </div>
              </form>
            </section>
          ) : (
            <p className="text-muted-foreground">Loading task details...</p>
          )}
        </CardContent>
      </Card>
      <section className="mt-8">
        <Card>
          <CardHeader>
            <CardTitle className="text-2xl">
              <div className="flex flex-1 justify-between">
                <p className="text-2xl mb-2">
                  Scripts related
                  <HoverCard>
                    <HoverCardTrigger asChild>
                      <Button variant="link">
                        <BadgeQuestionMarkIcon />
                      </Button>
                    </HoverCardTrigger>
                    <HoverCardContent className="w-80">
                      <div className="flex justify-between gap-4">
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
                  <Link href={`/tasks/create?taskId=${taskId}`}>
                    <PlusIcon size={20} /> Script
                  </Link>
                </Button>
              </div>
            </CardTitle>
          </CardHeader>
          <CardContent>
            <Table border={1} className="w-full">
              <TableCaption>All tasks related to this machine</TableCaption>
              <TableHeader className="border-y-0">
                <TableRow className="[&>*]:font-bold">
                  <TableHead className="flex items-center">
                    <Checkbox />
                  </TableHead>
                  <TableHead>ID</TableHead>
                  <TableHead>Version</TableHead>
                  <TableHead>Instruction</TableHead>
                  <TableHead>Active</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {scripts.length > 0 ? (
                  scripts?.map((script) => (
                    <TableRow
                      key={script.id}
                      onDoubleClick={() => router.push(`/scripts/${script.id}`)}
                    >
                      <TableCell className="flex items-center">
                        <Checkbox value={script.id} />
                      </TableCell>
                      <TableCell>{script.id}</TableCell>
                      <TableCell>{script.version}</TableCell>
                      <TableCell>
                        <HoverCard>
                          <HoverCardTrigger asChild>
                            <Button variant="link">
                              <pre className="text-ellipsis line-clamp-1">
                                {script.instruction}
                              </pre>
                            </Button>
                          </HoverCardTrigger>
                          <HoverCardContent className="w-fit">
                            <pre className="text-sm">{script.instruction}</pre>
                          </HoverCardContent>
                        </HoverCard>
                      </TableCell>
                      <TableCell>
                        {script.active ? (
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
                      No scripts found
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </section>
    </main>
  );
}
