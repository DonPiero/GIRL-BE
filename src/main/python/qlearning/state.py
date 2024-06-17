from abc import ABC, abstractmethod
class State(ABC):

    @abstractmethod
    def get_possible_actions(self):
        # actions are natural numbers
        # first action is 0, the second is 1, ...
        pass

    @abstractmethod
    def is_final(self):
        # returns 0 if epoch can continue
        # returns 1 if epoch if solution reached 
        # returns > 1, otherwise
        pass

    @abstractmethod
    def get_next(self, action):
        pass

    @abstractmethod
    def get_id(self):
        pass