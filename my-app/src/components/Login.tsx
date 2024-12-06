import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);

  useEffect(() => {
    // Check if authToken exists in localStorage
    const token = localStorage.getItem('authToken');
    if (token) {
      setIsAuthorized(true);
    }
  }, []);

  const handleLogin = async () => {
    setError(null); // Reset error before making the request

    try {
      const response = await axios.post('http://localhost:3001/profiles/login', {
        username,
        password,
      });

      const { jwtToken } = response.data;
      if (jwtToken) {
        const tokenPayload = JSON.parse(
            atob(jwtToken.split('.')[1]) // Decode the base64 payload
          );
      const expirationTime = tokenPayload.exp * 1000; 
      console.log("the expioration time is "+expirationTime);

    localStorage.setItem('authToken', JSON.stringify({ token: jwtToken, expiresAt: expirationTime }));
        alert('Login successful! with'+jwtToken);
        window.location.href = '/';
    } else {
        setError('Failed to retrieve token');
      }
    } catch (err) {
      console.error(err);
      setError('Login failed. Please check your username and password.');
    }
  };

  if (isAuthorized) {
    return <p>You need to be logged out to login.</p>;
  }

  return (
    <div style={{ marginBottom: '20px' }}>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        style={{ marginRight: '10px' }}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        style={{ marginRight: '10px' }}
      />
      <button onClick={handleLogin}>Login</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      <div>
        <button onClick={handleSignUp} style={{ marginLeft: '10px' }}>
          Signup
        </button>
      </div>
    </div>
  );
};

const handleSignUp = () => {

  window.location.href = '/signup';

};

export default Login;