class Writer < ApplicationRecord
    validates :name, :website, presence: true
end
