require 'calabash-android/calabash_steps'

When(/^I press key (\d+)$/) do |arg1|
  performAction("press_key", 7+arg1.to_i)
end

When(/^I enter "([^"]*)"$/) do |arg1|
  arg1.split("").each do |i|
    performAction("press_key", 7+i.to_i)
  end
end

When(/^I touch Prev/) do ||
  touch("AppCompatButton id:'btnPrev'")
end

Then(/^I check button Prev disabled$/) do
  check_element_exists("AppCompatButton id:'btnPrev' enabled:'false'")
end

Then(/^I check button Prev enabled$/) do
  check_element_exists("AppCompatButton id:'btnPrev' enabled:'true'")
end

Then(/^I check button Next disabled$/) do
  check_element_exists("AppCompatButton id:'btnNext' enabled:'false'")
end

Then(/^I check button Next enabled$/) do
  check_element_exists("AppCompatButton id:'btnNext' enabled:'true'")
end

Then(/^I check button Done disabled$/) do
  check_element_exists("AppCompatButton id:'btnDone' enabled:'false'")
end

Then(/^I check button Done enabled$/) do
  check_element_exists("AppCompatButton id:'btnDone' enabled:'true'")
end

Then(/^I see icon unknown$/) do
  check_element_exists("CreditEditText contentDescription:'unknown'")
end

Then(/^I see icon mastercardfront$/) do
  check_element_exists("CreditEditText contentDescription:'mastercardfront'")
end

Then(/^I see icon visafront$/) do
  check_element_exists("CreditEditText contentDescription:'visafront'")
end






