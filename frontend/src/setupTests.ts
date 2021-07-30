import "@testing-library/jest-dom";

import server from "__mock__/server";
import { queryClient } from "__mock__/utils/testUtils";

beforeAll(() => {
  server.listen();
});

afterEach(() => {
  queryClient.clear();
});

afterAll(() => {
  server.close();
});
