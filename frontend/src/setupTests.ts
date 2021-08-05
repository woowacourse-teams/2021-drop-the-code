import "@testing-library/jest-dom";

import server from "__mock__/server";
import { clearToken } from "__mock__/utils/mockingToken";
import { queryClient } from "__mock__/utils/testUtils";

beforeAll(() => {
  server.listen();
});

afterEach(() => {
  queryClient.clear();
  clearToken();
});

afterAll(() => {
  server.close();
});
