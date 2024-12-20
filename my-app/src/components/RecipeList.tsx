import React from "react";
import RecipeCard from "./RecipeCard";
import "./RecipeList.css"; // Per gli stili del layout

const RecipeList = ({ recipes }) => {
  return (
    <div className="recipe-list">
      {recipes.map((recipe, index) => (
        <RecipeCard
          key={index}
          title={recipe.title}
          description={recipe.description}
          imageUrl={recipe.imageUrl}
        />
      ))}
    </div>
  );
};

export default RecipeList;
