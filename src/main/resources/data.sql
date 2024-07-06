INSERT INTO Users (id, profilePicture, firstName, lastName,  rank, ongoingExp, completedExp, email, prefix, phoneNumber, country, city, password) VALUES
        (11, 'https://picsum.photos/600/600', 'Razvan', 'Petru', 3, 10, 500, 'test1@example.com', '+40', '75345678', 'Italy', 'Roma', 'alabala'),
        (12, 'https://picsum.photos/600/600', 'Andrei', 'Veli', 4, 5, 400, 'test2@example.com', '+40', '757654321', 'Spain', 'Madrid', 'portocala');

INSERT INTO Experiments (id, name, date, userId, experiment_state, model, instance, epsilon, decay, alpha, discount, runs, epochs, experiment_limit, generate_graph) VALUES
      (11, 'Experiment 1', '2024-01-01', 11, 0, 1, 'Instance 1', 5, 2, 10, 20, 50, 100, 1000, TRUE),
      (12, 'Experiment 2', '2024-02-01', 12, 1, 2, 'Instance 2', 3, 1, 15, 25, 60, 200, 1500, FALSE),
      (13, 'Experiment 3', '2024-03-01', 11, 0, 3, 'Instance 3', 4, 3, 20, 30, 70, 300, 2000, FALSE),
      (14, 'Experiment 4', '2024-04-01', 12, 0, 4, 'Instance 4', 6, 4, 25, 35, 80, 400, 2500, FALSE);
