import React, { useState } from "react";
import { FaTrash } from "react-icons/fa";
import "../../CSS/shopping-list-card.css";
import { useNavigate } from "react-router-dom";

interface ShoppingList {
  //finire di definire l'interfaccia
  name: String;
  ingredients : Array<Ingredient>;
}

interface Ingredient {
  id: number;
  name: String;
  quantity: number;
  unit: String;
}

const ShoppingListCard = ({list, onDelete}) => {
  const [listName, setListName] = useState(list.name);
  const navigate = useNavigate();
  
  const handleCardClick = () => {
    console.log(listName);
    navigate(`/shopping-list/${encodeURIComponent(listName)}`);
  };

  return (
    <div className="shopping-list-card" onClick = {handleCardClick}>
      <span className="shopping-list-name">{list.name}</span>
      <button className="delete-button" onClick={() => onDelete(list.name)}>
        <FaTrash/>
      </button>
    </div>
  );
};

export default ShoppingListCard;