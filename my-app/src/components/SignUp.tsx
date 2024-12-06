import React, { useState, useEffect  } from 'react';
import axios from 'axios';

export enum Gender {
    Male = "Male",
    Female = "Female"
}
const UserSubscription: React.FC = () => {
  const [email, setEmail] = useState('');
  const [gender, setGender] = useState<Gender | string>('');
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);

  useEffect(() => {
    // Check if authToken exists in localStorage
    const token = localStorage.getItem('authToken');
    if (token) {
      setIsAuthorized(true);
    }
  }, []);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    // Prepare the request body
    const body = {
      email,
      gender,
      password,
      username,
    };

    try {
      // Send POST request to create the user profile
      const response = await axios.post('http://localhost:3001/profiles/create', body);
      
      // If successful, handle the success response
      setSuccessMessage(`Profile created successfully: ${JSON.stringify(response.data)}`);
      setError('');
      alert(`Profile ${username} created correctly!`);
      window.location.href = '/login';
    } catch (err: any) {
      // Handle errors (e.g., "Profile already created")
      if (err.response && err.response.status === 406) {
        setError(`Error: ${err.response.data}`);
      } else {
        setError('An unexpected error occurred');
      }
      setSuccessMessage('');
    }
  };

  if (isAuthorized) {
    return <p>You need to be logged out to create a profile.</p>;
  }


  return (
    <div>
      <h2>User Subscription</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input 
            type="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            required 
          />
        </div>
        
        <div>
          <label>Gender:</label>
          <select 
            value={gender} 
            onChange={(e) => setGender(e.target.value)} 
            required
          >
            <option value="">Select Gender</option>
            <option value={Gender.Male}>{Gender.Male}</option>
            <option value={Gender.Female}>{Gender.Female}</option>
          </select>
        </div>

        <div>
          <label>Password:</label>
          <input 
            type="password" 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            required 
          />
        </div>

        <div>
          <label>Username:</label>
          <input 
            type="text" 
            value={username} 
            onChange={(e) => setUsername(e.target.value)} 
            required 
          />
        </div>

        <button type="submit">Create Profile</button>
      </form>

      {error && <p style={{ color: 'red' }}>{error}</p>}
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
        <div>
          <button onClick={handleLogin} style={{ marginLeft: '10px' }}>
              Login
          </button>
        </div>
    </div>
            
  );
};

const handleLogin = () => {

  window.location.href = '/login';

};

export default UserSubscription;