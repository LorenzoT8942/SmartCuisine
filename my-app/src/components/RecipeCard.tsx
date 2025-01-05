import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../CSS/RecipeCard.css";

const RecipeCard = ({ title, id, imageUrl }) => {
  const [recipeId, setRecipeId] = useState("");
  const [imgSrc, setImgSrc] = useState(imageUrl);
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/recipes/info/${id}`);
  };

  useEffect(() => {
    setImgSrc(imageUrl);
    setRecipeId(id);
  }, [imageUrl, id]);

  return (
    <div className="recipe-card" onClick={handleClick}>
      <img className="recipe-card__image" src={imgSrc} alt={title} />
      <div className="recipe-card__content">
        <h3 className="recipe-card__title">{title}</h3>
      </div>
    </div>
  );
};

export default RecipeCard;
