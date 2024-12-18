import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { UserProfile } from '../types/UserProfile.ts';
import { Notification } from '../types/Notification.ts';
import '../CSS/user-profile.css';

interface UserProfileProps {
  username: string;
}
export enum Gender {
  Male = "Male",
  Female = "Female"
}
const UserProfileComp: React.FC<UserProfileProps> = ({ username }) => {
  const [userProfile, setUserProfile] = useState<UserProfile | null>(null);
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [editedFields, setEditedFields] = useState<Partial<UserProfile & { password: string }>>({});


  useEffect(() => {
    const token = localStorage.getItem('authToken');
    console.log("profile of token "+ token);
    // Fetch user profile data
    const fetchUserProfile = async () => {
      try {
        if(token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        const response = await axios.get(`http://localhost:3001/profiles/profile`, {
            headers: {
              Authorization: `Bearer ${tokenParsed}` // Bearer token for authentication
            }
          });
        console.log(response.data);
        setUserProfile(response.data);
        setNotifications(response.data.notifications || []);
      } catch (err) {
        setError('Failed to fetch user profile');
        console.error(err);
      }
    };


    fetchUserProfile();

    setIsLoading(false);
  }, [username]);



  const handleFieldChange = (field: keyof UserProfile | 'password', value: string) => {
    setEditedFields((prev) => ({ ...prev, [field]: value }));
  };

  const handleUpdate = async () => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      alert('Authentication token is missing.');
      return;
    }

    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    const username = userProfile?.username;

    if (!username) {
      alert('Username is missing.');
      return;
    }
    try {
      const response = await axios.put(
        `http://localhost:3001/profiles/profile/${username}`,
        editedFields,
        {
          headers: {
            Authorization: `Bearer ${tokenParsed}`,
          },
        }
      );
      alert('Profile updated successfully!');
      setUserProfile((prev) => ({ ...prev, ...editedFields } as UserProfile));
      setEditedFields({});
      window.location.reload();

    } catch (err) {
      console.error('Failed to update profile:', err);
      alert('Failed to update profile: '+ err.response.data);
    }
  };
  const handleDelete = async () => {
    const token = localStorage.getItem('authToken');
    if (!token) {
      alert('Authentication token is missing.');
      return;
    }
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    const username = userProfile?.username;

    if (!username) {
      alert('Username is missing.');
      return;
    }
    try {
      const response = await axios.delete(
        `http://localhost:3001/profiles/profile/${username}`,
        {
          headers: {
            Authorization: `Bearer ${tokenParsed}`,
          },
        }
      );
      alert('Profile deleted successfully!');
      localStorage.removeItem('authToken');
      window.location.href = "/signup";

    } catch (err) {
      console.error('Failed to delete profile:', err);
      alert('Failed to delete profile: '+ err.response.data);
    }
  };

  if (isLoading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  if (!userProfile) {
    return <div className="no-data">No user profile data available.</div>;
  }

  return (
    <div className="profile-container">
      <h1 className="profile-header">{userProfile.username}'s Profile</h1>
      <div className="form-container">
      <div className="form-group">
        <label>
          Email:
        </label>
          <input
            type="email"
            value={editedFields.email ?? userProfile.email}
            onChange={(e) => handleFieldChange('email', e.target.value)}
          />
      </div>
      <div className="form-group">
        <label>
          Gender:
          </label>

          <select
            value={editedFields.gender ?? userProfile.gender}
            onChange={(e) => handleFieldChange('gender', e.target.value)}
          >
            <option value="">Select Gender</option>
            <option value={Gender.Male}>{Gender.Male}</option>
            <option value={Gender.Female}>{Gender.Female}</option>
          </select>
      </div>
      <div className="form-group">
        <label>
          Password:
          </label>

          <input
            type="password"
            value={editedFields.password ?? ''}
            onChange={(e) => handleFieldChange('password', e.target.value)}
          />
      </div>
      <button className="update-button" onClick={handleUpdate} disabled={Object.keys(editedFields).length === 0}>
        Update
      </button>
      </div>

      <h3>Notifications</h3>
      <ul className="notifications-list">
        {notifications.length === 0 ? (
          <li>No notifications</li>
        ) : (
          notifications.map((notification) => (
            <li key={notification.notificationId}>
              <p>{notification.content}</p>
            </li>
          ))
        )}
      </ul>

      <div className="delete-button-container">
        <button className="delete-button" onClick={handleDelete}>
          Delete profile
        </button>
      </div>
    </div>
  );
};

export default UserProfileComp;
