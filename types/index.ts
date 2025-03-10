export type User = {
  id: string;
  username: string;
  displayName: string;
  bio?: string;
  profileImage?: string;
  headerImage?: string;
  followersCount: number;
  followingCount: number;
  verified: boolean;
  createdAt: string;
};

export type Post = {
  id: string;
  content: string;
  user: User;
  createdAt: string;
  likesCount: number;
  repostsCount: number;
  repliesCount: number;
  isReply?: boolean;
  isRepost?: boolean;
}; 