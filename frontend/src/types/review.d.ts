export interface ReviewRequestFormData {
  studentId: number;
  teacherId: number;
  title: string;
  prUrl: string;
  content: string;
}

export type Progress = "ON_GOING" | "TEACHER_COMPLETED" | "FINISHED";

export interface Review {
  id: number;
  title: string;
  content: string;
  progress: Progress;
  teacherProfile: {
    id: number;
    name: string;
    imageUrl: string;
  };
  studentProfile: {
    id: number;
    name: string;
    imageUrl: string;
  };
  createAt: [number, number, number];
}

export type ReviewListMode = "teacher" | "student";
