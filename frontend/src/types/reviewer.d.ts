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
  githubUrl: string;
  career: number;
  sumReviewCount: number;
  averageReviewTime: number | null;
  title: string;
  techSpec: TechSpec;
}

export interface ReviewerRegisterFormData {
  techSpecs: {
    language: string;
    skills: string[];
  }[];
  career: string;
  title: string;
  content: string;
}

export interface StudentProfile {
  id: number;
  name: string;
  imageUrl: string;
}
export interface Feedback {
  id: number;
  star: number;
  comment: string;
  studentProfile: StudentProfile;
}

export type ReviewerSortOption = "career,desc" | "averageReviewTime,asc" | "sumReviewCount,desc" | null;
