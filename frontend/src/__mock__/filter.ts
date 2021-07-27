import { Language } from "../types/reviewer";

export const languages: Language[] = [
  {
    language: {
      id: 1,
      name: "java",
    },
    skills: [
      {
        id: 1,
        name: "spring",
      },
      {
        id: 2,
        name: "jpa",
      },
    ],
  },
  {
    language: {
      id: 2,
      name: "javascript",
    },
    skills: [
      {
        id: 3,
        name: "vue",
      },
      {
        id: 4,
        name: "react",
      },
    ],
  },
];
