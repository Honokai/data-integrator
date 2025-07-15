"use client";

import ReactCodeMirror, { ViewUpdate } from "@uiw/react-codemirror";
import { python } from "@codemirror/lang-python";
import { vscodeDark } from "@uiw/codemirror-theme-vscode";
import { Button } from "./ui/button";
import { RefreshCcw, Terminal, TriangleAlert } from "lucide-react";
import apiClient from "@/services/api/apiClient";
import React from "react";
import { useTheme } from "next-themes";
import { Alert, AlertDescription, AlertTitle } from "./ui/alert";

type PythonCodeEditorProps = {
  className?: string;
  code?: string;
  onChange: (val: string, viewUpdate: ViewUpdate) => void;
};

export default function PythonCodeEditor({
  className,
  code,
  onChange,
}: PythonCodeEditorProps) {
  const { theme } = useTheme();
  const [error, setError] = React.useState<{
    state: "error" | "success" | null;
    message: string;
  }>({ state: null, message: "" });

  async function evaluateCode() {
    const { data } = await apiClient.post("/scripts/evaluate", {
      code: code,
    });

    if (data?.success == false) {
      setError({ state: "error", message: data.message });
      return;
    }

    setError({ state: "success", message: "Code compiled successfully" });

    setTimeout(() => {
      setError({ state: null, message: "" });
    }, 3000);
  }

  return (
    <>
      <ReactCodeMirror
        className={className}
        extensions={[python()]}
        height="24rem"
        value={code}
        onChange={onChange}
        theme={theme == "light" ? "light" : vscodeDark}
        placeholder="Write your best python code here..."
      />
      {error.state != null && (
        <Alert
          className={`mt-2 ${
            error.state == "error" ? "text-danger" : "text-success"
          }`}
        >
          <Terminal />
          <AlertTitle>
            {error.state === "error" ? "Failing" : "Success"}
          </AlertTitle>
          <AlertDescription>
            <pre>{error.message}</pre>
          </AlertDescription>
        </Alert>
      )}

      <div className="flex justify-between">
        <p className="pt-2 flex gap-2 text-yellow-600 items-center">
          <TriangleAlert size={20} /> Não é possível utilizar imports
        </p>
        <Button
          type="button"
          variant="ghost"
          className="my-2"
          onClick={evaluateCode}
        >
          <RefreshCcw /> Evaluate code syntax
        </Button>
      </div>
    </>
  );
}
