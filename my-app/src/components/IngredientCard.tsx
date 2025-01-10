import React from 'react';
import { FaTrash } from 'react-icons/fa';
import '../CSS/ingredient-card.css';

interface IngredientCardProps {
    id: number;
    name: string;
    quantity: number;
    expirationDate: string | null;
    onDelete: (ingredientId: number) => void;
}

const IngredientCard: React.FC<IngredientCardProps> = ({ id, name, quantity, expirationDate, onDelete }) => {
    return (
        <div className="ingredient-card">
            <div className="ingredient-info">
                <div className="ingredient-details">
                    <h3>{name}</h3>
                    <p>Quantity: {quantity} grams</p>
                    {expirationDate && <p>Expiration Date: {expirationDate}</p>}
                </div>
                <button className="remove-button" onClick={() => onDelete(id)}>
                    <FaTrash />
                </button>
            </div>
        </div>
    );
};

export default IngredientCard;