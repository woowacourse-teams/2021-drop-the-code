export interface Language {
  language: {
    id: number;
    name: string;
  };
  skills: {
    id: number;
    name: string;
  }[];
}

export interface TechSpec {
  languages: {
    id: number;
    name: string;
  }[];
  skills: {
    id: number;
    name: string;
  }[];
}

export interface Reviewer {
  id: number;
  email: string;
  name: string;
  content: string;
  imageUrl: string;
  career: number;
  sumReviewCount: number;
  averageReviewTime: number | null;
  title: string;
  techSpec: TechSpec;
}

export type ReviewerSortOption = "career,desc" | "averageReviewTime,asc" | "sumReviewCount,desc" | null;
