export type Script = {
  id: string;
  version: number;
  instruction: string;
  active: boolean;
};

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
