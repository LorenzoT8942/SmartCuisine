import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { UserProfile } from '../types/UserProfile.ts';
import { Notification } from '../types/Notification.ts';

interface UserProfileProps {
  username: string;
}

const UserProfileComp: React.FC<UserProfileProps> = ({ username }) => {
  const [userProfile, setUserProfile] = useState<UserProfile | null>(null);
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

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

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!userProfile) {
    return <div>No user profile data available.</div>;
  }

  return (
    <div>
      <h1>{userProfile.username}'s Profile</h1>
      <p>Email: {userProfile.email}</p>
      <p>Gender: {userProfile.gender}</p>
      <h3>Notifications</h3>
      <ul>
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
    </div>
  );
};

export default UserProfileComp;
