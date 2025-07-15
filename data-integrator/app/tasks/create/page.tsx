"use client";

import { Task } from "@/app/machines/[machineId]/page";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

import apiClient from "@/services/api/apiClient";
import { useSearchParams } from "next/navigation";

import { useCallback, useEffect, useState } from "react";

export default function TaskCreatePage() {
  const machineId = useSearchParams().get("machineId") ?? "";

  const [task, setTask] = useState<Task>({
    id: "",
    machine: machineId,
    networkPath: "",
    singleFile: false,
    scanInterval: 5,
    active: false,
    fileFilter: {
      pattern: "",
      type: "CONTAINS",
    },
  });

  const [filterTypes, setFilterTypes] = useState<string[]>([]);

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
        await apiClient.post(`/tasks`, task);
        alert("Task created successfully!");
      } catch (error) {
        console.error("Error updating task:", error);
        alert("Failed to update task.");
      }
    }
  };

  useEffect(() => {
    fetchFilterTypes();
  }, []);

  return (
    <main className="flex-1 container mx-auto overflow-hidden w-full p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">Task</CardTitle>
        </CardHeader>
        <CardContent>
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
                        <SelectValue placeholder="CONTAINS" />
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
        </CardContent>
      </Card>
    </main>
  );
}
