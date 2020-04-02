class Writer < ApplicationRecord
    validates :name, :website, presence: true

    before_create :generate_website_shortened

    def generate_website_shortened
        client = Bitly::API::Client.new(token: "7000ae3c10755b540f1e1b05329808431ddfa975")
        bitlink = client.shorten(long_url: self.website)
        
        self.website_shortened = bitlink.link
    end
end
