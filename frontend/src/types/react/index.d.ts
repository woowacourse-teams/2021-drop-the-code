/// <reference types="react/next" />
/// <reference types="react-dom/next" />

import { ReactNode } from "react";

import { Writable } from "stream";

declare module "react-dom" {
  function hydrateRoot(container: HTMLElement, initialChildren: ReactNode, options?: HydrationOptions): Root;
}

declare module "react-dom/server" {
  function pipeToNodeWritable(
    children: ReactNode,
    destination: Writable,
    options: {
      identifierPrefix?: string;
      namespaceURI?: string;
      progressiveChunkSize?: number;
      onError?: () => void;
      onCompleteAll?: () => void;
      onReadyToStream?: () => void;
    }
  ): { startWriting: () => void; abort: () => void };
}
