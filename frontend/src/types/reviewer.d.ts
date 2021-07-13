export interface TechSpec {
  language: string;
  skills: string[];
}

export interface Reviewer {
  id: number;
  imageUrl: string;
  career: number;
  reviewCount: number;
  averageResponseTime: number | null;
  title: string;
  techSpec: TechSpec[];
}
