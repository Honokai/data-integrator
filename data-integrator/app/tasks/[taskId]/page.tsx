"use client";

import { Task } from "@/app/machines/[machineId]/page";
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
import { useParams } from "next/navigation";
import { useCallback, useEffect, useState } from "react";

export default function TaskPage() {
  const { taskId } = useParams<{ taskId: string }>();
  const [task, setTask] = useState<Task | null>(null);
  const [filterTypes, setFilterTypes] = useState<string[]>([]);

  const fetchTaskData = useCallback(async () => {
    const { data } = await apiClient.get(`/tasks/${taskId}`);

    if (data != null) {
      setTask(data);
    }
  }, [taskId]);

  const fetchFilterTypes = useCallback(async () => {
    const { data } = await apiClient.get("/enums/fileFilterTypes");

    if (data != null) {
      setFilterTypes(data);
    }
  }, []);

  useEffect(() => {
    fetchTaskData();
    fetchFilterTypes();
  }, [taskId, fetchTaskData]);

  return (
    <main className="flex-1 container mx-auto overflow-hidden w-full p-4">
      <header className="my-4">
        <h1 className="text-2xl">Task</h1>
        <h2 className="text-xl text-muted-foreground">{taskId}</h2>
      </header>
      <section title="Task Details Form Section" className="">
        {task ? (
          <div className="flex flex-col gap-4">
            <div className="grid lg:grid-cols-8 gap-4 md:grid-cols-6 sm:grid-cols-2">
              <div className="col-span-4">
                <Label className="mb-2" htmlFor="networkPath">
                  Network Path
                </Label>
                <Input
                  id="networkPath"
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
                <Select value={task.fileFilter.type}>
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
                  checked={task.active ? true : false}
                  onCheckedChange={() =>
                    setTask({
                      ...task,
                      active: !task.active,
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
                  checked={task.singleFile ? true : false}
                  onCheckedChange={() =>
                    setTask({
                      ...task,
                      singleFile: !task.singleFile,
                    })
                  }
                />
              </div>
            </div>
          </div>
        ) : (
          <p className="text-muted-foreground">Loading task details...</p>
        )}
      </section>
    </main>
  );
}
