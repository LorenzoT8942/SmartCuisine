import React from "react";
import RecipeCard from "./RecipeCard.tsx";
import "../CSS/RecipeList.css"; // Per gli stili del layout

const RecipeList = ({ recipes }) => {
  return (
    <div className="recipe-list">
      {recipes.map((recipe, index) => (
        <RecipeCard
          key={index}
          id = {recipe.id}
          title={recipe.title}
          imageUrl={recipe.imageUrl}
        />
      ))}
    </div>
  );
};

export default RecipeList;
