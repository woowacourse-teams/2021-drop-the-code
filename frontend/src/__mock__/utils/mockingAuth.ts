import { rest } from "msw";

import { studentAuth, teacherAuth } from "__mock__/data/auth";
import server from "__mock__/server";

export const mockingStudentAuth = () => {
  server.use(
    rest.get("/members/me", (req, res, ctx) => {
      return res.once(ctx.json(studentAuth));
    })
  );
};

export const mockingTeacherAuth = () => {
  server.use(
    rest.get("/members/me", (req, res, ctx) => {
      return res.once(ctx.json(teacherAuth));
    })
  );
};

export const mockingAnonymousAuth = () => {
  server.use(
    rest.get("/members/me", (req, res, ctx) => {
      return res.once(ctx.status(400));
    })
  );
};
