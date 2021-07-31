import { rest } from "msw";
import { setupServer } from "msw/node";

import { languages } from "__mock__/data/languages";
import { reviewers } from "__mock__/data/reviewers";
import { reviews } from "__mock__/data/reviews";

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
  rest.get("/teachers/:teacherId", (req, res, ctx) => {
    return res(ctx.json(reviewers[0]));
  }),
  rest.get("/reviews", (req, res, ctx) => {
    return res(ctx.json({ reviews, pageCount: 10 }));
  }),
  rest.get("/reviews/teacher/:teacherId", (req, res, ctx) => {
    return res(ctx.json({ reviews, pageCount: 10 }));
  }),
];

const server = setupServer(...requestHandlers);

export default server;
