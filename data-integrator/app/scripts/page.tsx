"use client";

import PythonCodeEditor from "@/components/python-code-editor";
import { Textarea } from "@/components/ui/textarea";
import { ViewUpdate } from "@codemirror/view";
import React, { useMemo } from "react";

export default function ScriptsIndexPage() {
  const [textAreaValue, setTextAreaValue] = React.useState<string>("");
  const [code, setCode] = React.useState<string>("");

  const onChangeCode = React.useCallback(
    (val: string, viewUpdate: ViewUpdate) => {
      setCode(val);
    },
    []
  );

  const onChangeTextArea = React.useCallback(
    (ev: React.ChangeEvent<HTMLTextAreaElement>) => {
      setTextAreaValue(ev.target.value);
    },
    []
  );

  const memoizedCodeEditor = useMemo(() => {
    return (
      <PythonCodeEditor
        className="mt-4 flex-1 border-2 border-solid rounded-2xl overflow-hidden"
        code={code}
        onChange={onChangeCode}
      />
    );
  }, [code, onChangeCode]);

  return (
    <main className="flex-1 container mx-auto pt-6">
      <h1 className="text-2xl">Script builder screen</h1>
      <h2 className="text-lg text-muted-foreground">
        Escreva seu código, teste sua syntax e valide contra amostras de textos.
      </h2>
      <div className="grid grid-cols-2 gap-2">
        <section className="">
          <p className="text-xl pt-4">Seu código</p>
          {memoizedCodeEditor}
        </section>
        <section>
          <p className="text-xl pt-4">
            Conteúdo do arquivo de texto (csv, txt)
          </p>
          <Textarea
            className="flex-1 mt-4"
            placeholder="Deseja testar seu código Python? Digite aqui o texto de amostra a ser analisado."
            value={textAreaValue}
            onChange={onChangeTextArea}
            rows={9}
          />
        </section>
      </div>
    </main>
  );
}
