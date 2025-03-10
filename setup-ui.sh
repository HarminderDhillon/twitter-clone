#!/bin/bash

echo "Setting up Twitter Clone UI..."

# Install dependencies
echo "Installing dependencies..."
npm install

# Create missing directories if they don't exist
echo "Creating directory structure..."
mkdir -p app/api types lib components/ui components/posts app/users/[username]

# Create .env.local file for development
echo "Creating environment variables..."
cat > .env.local << EOF
NEXT_PUBLIC_API_URL=http://localhost:8080/api
EOF

echo "Setup completed! You can now start the development server with:"
echo "npm run dev"
echo 
echo "The UI will be available at http://localhost:3000" 