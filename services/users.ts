import { User } from '@/types';

export async function fetchUsers(): Promise<User[]> {
  try {
    console.log('Fetching users from API');
    const response = await fetch('/api/users', {
      headers: {
        'Accept': 'application/json'
      }
    });
    
    console.log('Users API response status:', response.status);
    
    if (!response.ok) {
      // Try to read the error message from the response
      let errorText = '';
      try {
        const errorData = await response.text();
        errorText = errorData;
      } catch (e) {
        console.error('Failed to parse error response:', e);
      }
      
      throw new Error(`Error fetching users: ${response.status} ${response.statusText}\n${errorText}`);
    }
    
    const data = await response.json();
    console.log('Users data received:', data);
    return data;
  } catch (error) {
    console.error('Error fetching users:', error);
    // Return mock data if API call fails
    console.warn('Falling back to mock users data');
    return getMockUsers();
  }
}

export async function fetchUserByUsername(username: string): Promise<User> {
  try {
    console.log(`Fetching user with username: ${username}`);
    const response = await fetch(`/api/users/${username}`, {
      headers: {
        'Accept': 'application/json'
      }
    });
    
    console.log(`User ${username} API response status:`, response.status);
    
    if (!response.ok) {
      // Try to read the error message from the response
      let errorText = '';
      try {
        const errorData = await response.text();
        errorText = errorData;
      } catch (e) {
        console.error('Failed to parse error response:', e);
      }
      
      throw new Error(`Error fetching user: ${response.status} ${response.statusText}\n${errorText}`);
    }
    
    const data = await response.json();
    console.log('User data received:', data);
    return data;
  } catch (error) {
    console.error(`Error fetching user ${username}:`, error);
    // Return mock data if API call fails
    console.warn('Falling back to mock user data');
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