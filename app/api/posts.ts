import { Post } from '@/types';

export async function fetchPosts(): Promise<Post[]> {
  try {
    const response = await fetch('/api/posts');
    if (!response.ok) {
      throw new Error(`Error fetching posts: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching posts:', error);
    // Return mock data if API call fails
    return getMockPosts();
  }
}

export async function fetchPostById(id: string): Promise<Post> {
  try {
    const response = await fetch(`/api/posts/${id}`);
    if (!response.ok) {
      throw new Error(`Error fetching post: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error(`Error fetching post ${id}:`, error);
    // Return mock data if API call fails
    return getMockPosts()[0];
  }
}

// Mock data for development and testing
function getMockPosts(): Post[] {
  return [
    {
      id: '1',
      content: 'Just deployed a new Spring Boot backend with Liquibase for database migrations! #SpringBoot #Java',
      createdAt: new Date().toISOString(),
      likesCount: 42,
      repostsCount: 12,
      repliesCount: 5,
      user: {
        id: '1',
        username: 'techdevloper',
        displayName: 'Tech Developer',
        bio: 'Java & Spring Boot enthusiast',
        profileImage: 'https://i.pravatar.cc/150?u=techdev1',
        followersCount: 1200,
        followingCount: 350,
        verified: true,
        createdAt: '2023-01-01T00:00:00Z'
      }
    },
    {
      id: '2',
      content: 'Learning Next.js and Tailwind CSS for our new project. The developer experience is amazing! 🚀',
      createdAt: new Date(Date.now() - 86400000).toISOString(),
      likesCount: 24,
      repostsCount: 5,
      repliesCount: 3,
      user: {
        id: '2',
        username: 'frontendwizard',
        displayName: 'Frontend Wizard',
        bio: 'UI/UX designer and frontend developer',
        profileImage: 'https://i.pravatar.cc/150?u=uiux2',
        followersCount: 850,
        followingCount: 420,
        verified: false,
        createdAt: '2023-02-15T00:00:00Z'
      }
    },
    {
      id: '3',
      content: 'Just released a new open-source library for handling database migrations. Check it out on GitHub!',
      createdAt: new Date(Date.now() - 172800000).toISOString(),
      likesCount: 78,
      repostsCount: 32,
      repliesCount: 14,
      user: {
        id: '3',
        username: 'opensourcehero',
        displayName: 'Open Source Hero',
        bio: 'Contributing to open source projects',
        profileImage: 'https://i.pravatar.cc/150?u=opensrc3',
        followersCount: 3400,
        followingCount: 125,
        verified: true,
        createdAt: '2022-08-10T00:00:00Z'
      }
    }
  ];
} 