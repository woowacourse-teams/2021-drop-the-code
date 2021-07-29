import "@testing-library/jest-dom";
import { setupServer } from "msw/node";

import requestHandlers from "__mock__/requestHandlers";

const server = setupServer(...requestHandlers);

beforeAll(() => {
  server.listen();
});

afterAll(() => {
  server.close();
});
