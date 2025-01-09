import React from "react";
import RecipeCard from "./RecipeCard.tsx";
import "../CSS/RecipeList.css"; // Per gli stili del layout
import { useLocation } from "react-router-dom";
import axios from "axios";

const RecipeList = ({ recipes, setRecipes  }) => {

  const token = localStorage.getItem('authToken');

  const location = useLocation(); // Get the current route path
  const handleDelete = async (id: number) => {
    if(token == null) throw new Error("the token is null");
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    try {
      const response = await axios.delete(`http://localhost:3001/profiles/profile/favorites/${id}`, {
        headers: {
          Authorization: `Bearer ${tokenParsed}`,
        },
      });
      console.log("Recipe deleted", response);
      setRecipes((prevRecipes) => prevRecipes.filter((recipe) => recipe.id !== id));

    } catch (error) {
      console.error("Error deleting recipe", error);
    }
  };
  
  

  return (
    <div className="recipe-list">
      {recipes.map((recipe) => (
        <div key={recipe.id}>
          <RecipeCard
            id={recipe.id}
            title={recipe.title}
            imageUrl={recipe.image}
          />
          {location.pathname === "/profile" && (
            <button
              onClick={() => handleDelete(recipe.id)}
              className="delete-button"
            >
              Remove
            </button>
          )}
          
        </div>
      ))}
    </div>
  );
};


export default RecipeList;
