import { User } from '@/types';

export async function fetchUsers(): Promise<User[]> {
  try {
    const response = await fetch('/api/users');
    if (!response.ok) {
      throw new Error(`Error fetching users: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('Error fetching users:', error);
    // Return mock data if API call fails
    return getMockUsers();
  }
}

export async function fetchUserByUsername(username: string): Promise<User> {
  try {
    const response = await fetch(`/api/users/username/${username}`);
    if (!response.ok) {
      throw new Error(`Error fetching user: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error(`Error fetching user ${username}:`, error);
    // Return mock data if API call fails
    return getMockUsers().find(user => user.username.toLowerCase() === username.toLowerCase()) || getMockUsers()[0];
  }
}

// Mock data for development and testing
function getMockUsers(): User[] {
  return [
    {
      id: '1',
      username: 'techdevloper',
      displayName: 'Tech Developer',
      bio: 'Java & Spring Boot enthusiast',
      profileImage: 'https://i.pravatar.cc/150?u=techdev1',
      followersCount: 1200,
      followingCount: 350,
      verified: true,
      createdAt: '2023-01-01T00:00:00Z'
    },
    {
      id: '2',
      username: 'frontendwizard',
      displayName: 'Frontend Wizard',
      bio: 'UI/UX designer and frontend developer',
      profileImage: 'https://i.pravatar.cc/150?u=uiux2',
      followersCount: 850,
      followingCount: 420,
      verified: false,
      createdAt: '2023-02-15T00:00:00Z'
    },
    {
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
  ];
} 