import React, { useState } from 'react';
import axios from 'axios';
import '../../CSS/ingredient-modal.css';

interface Ingredient {
    id: number;
    name: string;
    quantity: number;
    unit: string;
}

interface SelectedIngredient {
    id: number;
    ingredient: Ingredient;
    quantity: number;
}

interface IngredientModalProps {
    onClose: () => void;
    onAdd: (ingredient: SelectedIngredient) => void;
}

const IngredientModal: React.FC<IngredientModalProps> = ({ onClose, onAdd }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState<Ingredient[]>([]);
    const [selectedIngredients, setSelectedIngredients] = useState<SelectedIngredient[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const token = localStorage.getItem('authToken');

    const handleSearch = async () => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get(`http://localhost:3004/recipes-ingredients/ingredients/search-by-name?name=${searchTerm}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            setSearchResults(response.data);
            console.log(response.data);
        } catch (err) {
            setError('Error fetching ingredients');
        } finally {
            setLoading(false);
        }
    };

    const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            handleSearch();
        }
    };

    const handleAddIngredient = (ingredient: Ingredient) => {
        if (selectedIngredients.some(selected => selected.ingredient.id === ingredient.id)) {
            alert('This ingredient has already been added.');
            return;
        }
        const quantity = prompt(`Enter quantity in grams for ${ingredient.name}:`);
        if (quantity && !isNaN(Number(quantity))) {
            const selectedIngredient: SelectedIngredient = {
                id: ingredient.id,
                ingredient,
                quantity: parseFloat(quantity)
            };
            setSelectedIngredients([...selectedIngredients, selectedIngredient]);
        } else {
            alert('Please enter a valid number for the quantity.');
        }
    };

    const handleRemoveIngredient = (index: number) => {
        const updatedIngredients = selectedIngredients.filter((_, i) => i !== index);
        setSelectedIngredients(updatedIngredients);
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" onClick={onClose}>X</button>
                <h2>Search Ingredient</h2>
                <form onSubmit={(e) => e.preventDefault()}>
                    <input
                        type="text"
                        className="search-bar"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        onKeyPress={handleKeyPress}
                        placeholder="Search for an ingredient"
                    />
                </form>
                {loading && <p>Loading...</p>}
                {error && <p>{error}</p>}
                <div className="ingredient-selection">
                    <ul className="search-results">
                        {searchResults.length > 0 ? (
                            searchResults.map((ingredient) => (
                                <li key={ingredient.id} onClick={() => handleAddIngredient(ingredient)}>
                                    {ingredient.name}
                                </li>
                            ))
                        ) : (
                            <p>No ingredients found</p>
                        )}
                    </ul>
                    <div className="selected-ingredients">
                        <h3>Selected Ingredients</h3>
                        <ul>
                            {selectedIngredients.map((selected, index) => (
                                <li key={index}>
                                    {selected.ingredient.name}: {selected.quantity} grams
                                    <button className="remove-button" onClick={() => handleRemoveIngredient(index)}>X</button>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
                <button className="cancel-button" onClick={onClose}>Cancel</button>
            </div>
        </div>
    );
};

export default IngredientModal;