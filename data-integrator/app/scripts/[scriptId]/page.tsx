"use client";

import PythonCodeEditor from "@/components/python-code-editor";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Switch } from "@/components/ui/switch";
import { Textarea } from "@/components/ui/textarea";
import { useScript } from "@/hooks/script/useScript";
import useUpdateScript from "@/hooks/script/useUpdateScript";
import { Script } from "@/lib/types";
import apiClient from "@/services/api/apiClient";
import { ViewUpdate } from "@codemirror/view";
import { CopyIcon, FileTerminalIcon, SaveIcon, Terminal } from "lucide-react";
import Link from "next/link";
import { useParams } from "next/navigation";
import React, { useEffect, useMemo, useState } from "react";

export default function ScriptsShowPage() {
  const { scriptId } = useParams<{ scriptId: string }>();
  const updateScript = useUpdateScript(scriptId);
  const [textAreaValue, setTextAreaValue] = React.useState<string>("");

  const [testCodeStatus, setTestCodeStatus] = React.useState<{
    state: null | "success" | "error";
    message: string;
  }>({
    state: null,
    message: "",
  });

  const {
    data: script,
    isLoading: loadingScript,
    isError: errorScript,
    error: scriptError,
  } = useScript(scriptId);

  async function testCode() {
    const { data, status } = await apiClient.post<{
      state: string;
      message: string;
    }>(`/scripts/test`, {
      code: localScript?.instruction,
      contentToTestAgainst: textAreaValue,
    });

    if (status != 200) {
      setTestCodeStatus({ state: "error", message: "failed" });
      return;
    }

    setTestCodeStatus({ state: "success", message: data.message });
  }

  const onChangeTextArea = React.useCallback(
    (ev: React.ChangeEvent<HTMLTextAreaElement>) => {
      setTextAreaValue(ev.target.value);
    },
    []
  );

  const onFormSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (localScript) {
      updateScript.mutate(localScript);
    }
  };

  const [localScript, setLocalScript] = useState<Script | null>(null);

  const onChangeCode = React.useCallback(
    (val: string, viewUpdate: ViewUpdate) => {
      if (localScript) {
        setLocalScript({
          ...localScript,
          instruction: val,
        });
      }
    },
    [localScript]
  );

  const memoizedCodeEditor = useMemo(() => {
    return (
      <PythonCodeEditor
        className="mt-4 flex-1 border-2 border-solid rounded-2xl overflow-hidden h-96"
        code={localScript?.instruction ?? ""}
        onChange={onChangeCode}
      />
    );
  }, [localScript, onChangeCode]);

  useEffect(() => {
    if (script) {
      setLocalScript(script);
    }
  }, [script]);

  return (
    <main className="flex-1 container mx-auto pt-6">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl flex items-center gap-2">
            Script
            <Button variant="ghost" asChild className="p-0 m-0">
              <Link href={`/scripts/create?copy=${scriptId}`}>
                <CopyIcon size={20} />
              </Link>
            </Button>
          </CardTitle>
          <CardDescription className="text-muted-foreground flex justify-between">
            <span>Id: {localScript?.id}</span>
            <span>Version: {localScript?.version}</span>
          </CardDescription>
        </CardHeader>
        <CardContent>
          <section title="Script editable details">
            {localScript != null ? (
              <form action="#" onSubmit={onFormSubmit}>
                <div className="grid grid-cols-3 gap-5">
                  <div className="w-fit">
                    <Label htmlFor="version" className="mb-2">
                      Version
                    </Label>
                    <Input
                      id="version"
                      name="version"
                      readOnly
                      disabled
                      value={localScript?.version ?? "Loading..."}
                      className="cursor-not-allowed"
                    />
                  </div>
                  <div className="">
                    <Label htmlFor="active" className="mb-2">
                      Active
                    </Label>
                    <Switch
                      id="active"
                      name="active"
                      className="cursor-pointer"
                      checked={localScript?.active || false}
                      onCheckedChange={(checked) => {
                        setLocalScript({
                          ...localScript!,
                          active: checked as boolean,
                        });
                      }}
                    />
                  </div>
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <section title="Script code editor" className="">
                    <p className="text-xl pt-4">Your code</p>
                    {memoizedCodeEditor}
                  </section>
                  <section>
                    <p className="text-xl pt-4">File contents (csv, txt)</p>
                    <Textarea
                      className="flex-1 mt-4 font-mono h-96"
                      placeholder="Want to test your Python code? Enter the sample text to be analyzed here."
                      value={textAreaValue}
                      onChange={onChangeTextArea}
                      rows={9}
                    />
                    <div className="flex justify-end mt-2">
                      {testCodeStatus.state != null && (
                        <Alert className={`mt-2 text-success`}>
                          <Terminal />
                          <AlertTitle>Output</AlertTitle>
                          <AlertDescription>
                            <pre>{testCodeStatus.message}</pre>
                          </AlertDescription>
                        </Alert>
                      )}
                      <Button
                        variant="ghost"
                        type="button"
                        onClick={() => testCode()}
                      >
                        <FileTerminalIcon size={20} /> Run
                      </Button>
                    </div>
                  </section>
                </div>
                <div>
                  <Button type="submit">
                    <SaveIcon /> Save
                  </Button>
                </div>
              </form>
            ) : (
              <p>Loading script data</p>
            )}
          </section>
        </CardContent>
      </Card>
    </main>
  );
}
