import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { UserProfile } from '../types/UserProfile.ts';
import { Notification } from '../types/Notification.ts';
import '../CSS/user-profile.css';
import RecipeList from "./RecipeList.tsx";

interface UserProfileProps {
  username: string;
}
interface FavoriteRecipesResponseDTO {
  recipeIds: number[];
}
interface RecipeInfoResponseDTO {
  id: number;
  title: string;
  image: string;
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
  const [recipeIds, setRecipeIds] = useState<number[]>([]);
  const [recipes, setRecipes] = useState<RecipeInfoResponseDTO[]>([]);

  

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

    const fetchRecipeInfo = async (id: number, token: string) => {
      try {
        const response = await axios.get<RecipeInfoResponseDTO>(
          `http://localhost:3004/recipes-ingredients/recipes/info/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`, // Bearer token for authentication
            },
          }
        );
        const { id: recipeId, title, image } = response.data;
        return { id: recipeId, title, image }; // Return only the id, title, and image
      } catch (err) {
        console.error(err);
        setError(err instanceof Error ? err.message : "An unknown error occurred");
        return null; // Return null in case of an error
      }
    };

    const fetchFavorites = async () => {
      try {
        // Retrieve token
        if(token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
  
        // Perform GET request
        const response = await axios.get<FavoriteRecipesResponseDTO>(
          
          "http://localhost:3001/profiles/profile/favorites",
          {
            headers: {
              Authorization: `Bearer ${tokenParsed}`, // Bearer token for authentication
            },
          }
        );
  
        // Update state with the retrieved recipe IDs
        setRecipeIds(response.data.recipeIds);

        const recipePromises = response.data.recipeIds.map((id) =>
          fetchRecipeInfo(id, tokenParsed)
        );
        const recipesData = await Promise.all(recipePromises);
  
        // Filter out any null responses (in case of errors fetching some recipes)
        setRecipes(recipesData.filter((recipe) => recipe !== null) as RecipeInfoResponseDTO[]);


        
      } catch (err) {
        setError(err instanceof Error ? err.message : "An unknown error occurred");
        console.error(err);
      } 
    };


    fetchUserProfile();
    fetchFavorites();


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

      <div>
        <h3>Favorite Recipes</h3>
        {recipeIds.length === 0 ? (
          <p>No favorite recipes found.</p>
        ) : (
          <RecipeList recipes={recipes} setRecipes={setRecipes} />
        )}
      </div>

      <div className="delete-button-container">
        <button className="delete-button" onClick={handleDelete}>
          Delete profile
        </button>
      </div>
    </div>
  );
};

export default UserProfileComp;
