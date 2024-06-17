from abc import ABC, abstractmethod
class Model(ABC):

    @abstractmethod
    def get_no_of_actions(self):
        pass

    @abstractmethod
    def get_initial_state(self):
        pass

    @abstractmethod
    def get_reward(self, state, next_state):
        pass

