import React from "react";
import "./CSS/RecipeCard.css";

const RecipeCard = ({ title, description, imageUrl }) => {
  return (
    <div className="recipe-card">
      <img className="recipe-card__image" src={imageUrl} alt={title} />
      <div className="recipe-card__content">
        <h3 className="recipe-card__title">{title}</h3>
        <p className="recipe-card__description">{description}</p>
      </div>
    </div>
  );
};

export default RecipeCard;