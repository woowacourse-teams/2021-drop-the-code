import { rest } from "msw";

import { languages } from "__mock__/data/languages";
import { reviewers } from "__mock__/data/reviewers";

const requestHandlers = [
  rest.get("/members/me", (req, res, ctx) => {
    return res(ctx.status(200));
  }),
  rest.get("/languages", (req, res, ctx) => {
    return res(ctx.json(languages));
  }),
  rest.get("/teachers", (req, res, ctx) => {
    return res(ctx.json({ teacherProfiles: reviewers, pageCount: 10 }));
  }),
];

export default requestHandlers;
