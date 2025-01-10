import React, { useState } from 'react';
import axios from 'axios';

interface Ingredient {
    ingredientId: number;
    name: string;
}

interface SelectedIngredient extends Ingredient {
    quantity: number;
    expirationDate: Date;
}

interface AddIngredientModalProps {
    onClose: () => void;
    onSubmit: (quantity: number, expirationDate: string) => void;
}

const AddIngredientModal: React.FC<AddIngredientModalProps> = ({ onClose, onSubmit }) => {
    const [quantity, setQuantity] = useState<number | ''>('');
    const [expirationDate, setExpirationDate] = useState<string>('');
    const [formErrors, setFormErrors] = useState<{ quantity?: string; expirationDate?: string }>({});

    const validateForm = () => {
        const errors: { quantity?: string; expirationDate?: string } = {};

        if (quantity === '' || quantity <= 0) {
            errors.quantity = 'Please enter a valid quantity greater than 0.';
        }

        if (!expirationDate) {
            errors.expirationDate = 'Please select a valid expiration date.';
        }

        setFormErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSubmit = () => {
        if (validateForm()) {
            onSubmit(quantity as number, expirationDate);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Add Ingredient</h2>
                <form onSubmit={(e) => e.preventDefault()}>
                    <div className="form-group">
                        <label>
                            Quantity:
                            <input
                                type="number"
                                value={quantity}
                                onChange={(e) => setQuantity(Number(e.target.value) || '')}
                                placeholder="Enter quantity"
                                className={formErrors.quantity ? 'input-error' : ''}
                            />
                        </label>
                        {formErrors.quantity && <span className="error-message">{formErrors.quantity}</span>}
                    </div>
                    <div className="form-group">
                        <label>
                            Expiration Date:
                            <input
                                type="date"
                                value={expirationDate}
                                onChange={(e) => setExpirationDate(e.target.value)}
                                className={formErrors.expirationDate ? 'input-error' : ''}
                            />
                        </label>
                        {formErrors.expirationDate && (
                            <span className="error-message">{formErrors.expirationDate}</span>
                        )}
                    </div>
                    <div className="modal-actions">
                        <button type="button" onClick={handleSubmit}>
                            Add
                        </button>
                        <button type="button" onClick={onClose}>
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

interface IngredientShoppingModalProps {
    onClose: () => void;
}

const IngredientShoppingModal: React.FC<IngredientShoppingModalProps> = ({ onClose }) => {
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(false);
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [selectedIngredients, setSelectedIngredients] = useState<SelectedIngredient[]>([]);
    const [isAddModalOpen, setAddModalOpen] = useState(false);
    const [currentIngredient, setCurrentIngredient] = useState<Ingredient | null>(null);

    const token = localStorage.getItem('authToken');

    const handleSearch = async () => {
        if (!token) throw new Error('The token is null');
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;

        setLoading(true);
        try {
            const response = await axios.get(
                `http://localhost:3004/recipes-ingredients/ingredients/search-by-name?name=${searchTerm}`,
                { headers: { Authorization: `Bearer ${tokenParsed}` } }
            );
            setIngredients(response.data);
        } catch (error) {
            console.error('Error fetching ingredients:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleAddIngredientClick = (ingredient: Ingredient) => {
        setCurrentIngredient(ingredient);
        setAddModalOpen(true);
    };

    const handleAddIngredient = (quantity: number, expirationDate: string) => {
        if (currentIngredient) {
            setSelectedIngredients((prev) => [
                ...prev,
                { ...currentIngredient, quantity, expirationDate: new Date(expirationDate) },
            ]);
            setAddModalOpen(false);
            setCurrentIngredient(null);
        }
    };

    const handleRemoveIngredient = (index: number) => {
        setSelectedIngredients((prev) => prev.filter((_, i) => i !== index));
    };

    const handleConfirm = async () => {
        if (!token) throw new Error('The token is null');
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;

        try {
            await Promise.all(
                selectedIngredients.map((ingredient) =>
                    axios.post(
                        'http://localhost:3002/shopping-lists/add-ingredient',
                        {
                            username: 'fixed_value',
                            ingredientId: ingredient.ingredientId,
                            quantity: ingredient.quantity,
                            expirationDate: ingredient.expirationDate.toISOString().split('T')[0],
                        },
                        { headers: { Authorization: `Bearer ${tokenParsed}` } }
                    )
                )
            );
            onClose();
        } catch (error) {
            console.error('Error confirming ingredients:', error);
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" onClick={onClose}>
                    X
                </button>
                <h2>Search Ingredient</h2>
                <form onSubmit={(e) => e.preventDefault()}>
                    <input
                        type="text"
                        className="search-bar"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                        placeholder="Search for an ingredient"
                    />
                </form>
                {loading && <p>Loading...</p>}
                <ul>
                    {ingredients.map((ingredient) => (
                        <li key={ingredient.ingredientId}>
                            {ingredient.name}
                            <button onClick={() => handleAddIngredientClick(ingredient)}>Add</button>
                        </li>
                    ))}
                </ul>
                <h2>Selected Ingredients</h2>
                <ul>
                    {selectedIngredients.map((ingredient, index) => (
                        <li key={ingredient.ingredientId}>
                            {ingredient.name} - {ingredient.quantity} -{' '}
                            {ingredient.expirationDate.toISOString().split('T')[0]}
                            <button onClick={() => handleRemoveIngredient(index)}>Remove</button>
                        </li>
                    ))}
                </ul>
                <button onClick={handleConfirm}>Confirm</button>
                {isAddModalOpen && (
                    <AddIngredientModal
                        onClose={() => setAddModalOpen(false)}
                        onSubmit={handleAddIngredient}
                    />
                )}
            </div>
        </div>
    );
};

export default IngredientShoppingModal;
